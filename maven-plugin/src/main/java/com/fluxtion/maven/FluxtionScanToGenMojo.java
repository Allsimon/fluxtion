/*
 * Copyright (C) 2019 V12 Technology Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.maven;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import net.openhft.compiler.CompilerUtils;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 *
 * @author V12 Technology Ltd.
 */
@Mojo(name = "scan",
        requiresProject = true,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        defaultPhase = LifecyclePhase.COMPILE
)
public class FluxtionScanToGenMojo extends AbstractMojo {

    /**
     * The output directory for build artifacts generated by fluxtion. Absolute
     * paths are preceded with "/" otherwise the path relative to the project
     * root directory
     */
    @Parameter(property = "buildDirectory", defaultValue = "target/classes")
    private String buildDirectory;

    private URLClassLoader classLoader;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (System.getProperty("skipFluxtion") != null) {
            getLog().info("Fluxtion generation skipped.");
        } else {
            try {
                if (buildDirectory == null) {
                    buildDirectory = project.getBasedir().getCanonicalPath() + "/target/classes";
                } else if (!buildDirectory.startsWith("/")) {
                    buildDirectory = project.getBasedir().getCanonicalPath() + "/" + buildDirectory;
                }
                buildFluxtionClassLoader();
                //generate static context
                Class<BiConsumer<URL, File>> apClazz = (Class<BiConsumer<URL, File>>) classLoader.loadClass("com.fluxtion.generator.compiler.ClassProcessorDispatcher");
                apClazz.newInstance().accept(new File(buildDirectory).toURI().toURL(),  project.getBasedir());
            } catch (Exception exception) {
                getLog().error(exception);
                throw new MojoExecutionException("problem setting building fluxtion class loader", exception);
            }
        }
    }

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    private URLClassLoader buildFluxtionClassLoader() throws MojoExecutionException, MalformedURLException, DependencyResolutionRequiredException {
        List<String> elements = project.getCompileClasspathElements();

        URL[] urls = new URL[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            File cpFile = new File(elements.get(i));
            getLog().debug("Adding element from runtime to classpath:" + cpFile.getPath());
            if (cpFile.isDirectory()) {
                urls[i] = cpFile.toURI().toURL();
            } else {
                urls[i] = new URL("jar:" + cpFile.toURI().toURL() + "!/");
            }
            CompilerUtils.addClassPath(cpFile.getPath());
        }
        getLog().debug("user classpath URL list:" + Arrays.toString(urls));
        classLoader = URLClassLoader.newInstance(urls);
        return classLoader;
    }
}
