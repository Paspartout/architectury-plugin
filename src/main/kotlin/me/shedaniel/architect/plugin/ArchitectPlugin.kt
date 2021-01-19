package me.shedaniel.architect.plugin

import net.fabricmc.loom.util.LoggerFilter
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URI

class ArchitectPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        LoggerFilter.replaceSystemOut()

        project.apply(
            mapOf(
                "plugin" to "java",
                "plugin" to "eclipse",
                "plugin" to "idea"
            )
        )

        project.extensions.create("architect", ArchitectPluginExtension::class.java, project)
        project.extensions.add("architectury", project.extensions.getByName("architect"))

        project.tasks.register("transformForge", RemapMCPTask::class.java) {
            it.fakeMod = false
            it.remapMcp = false
            it.group = "Architectury"
        }

        project.tasks.register("transformForgeFakeMod", RemapMCPTask::class.java) {
            it.fakeMod = true
            it.remapMcp = false
            it.group = "Architectury"
        }

        project.tasks.register("transformArchitectJar", TransformTask::class.java) {
            it.group = "Architectury"
            it.runtime = false
        }

        project.tasks.register("transformArchitectJarRuntime", TransformTask::class.java) {
            it.group = "Architectury"
            it.runtime = true
        }

        project.repositories.apply {
            mavenCentral()
            jcenter()
            maven { it.url = URI("https://dl.bintray.com/shedaniel/cloth") }
        }
    }
}
