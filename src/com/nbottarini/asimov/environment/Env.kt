package com.nbottarini.asimov.environment

import io.github.cdimascio.dotenv.dotenv
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object Env {
    private val searchPaths = mutableListOf<String>()
    private val dotenv by lazy {
        dotenv {
            ignoreIfMissing = true
            directory = findEnvDirectory()
        }
    }

    fun addSearchPath(path: String) { searchPaths.add(path) }

    operator fun get(name: String): String? = dotenv[name]

    operator fun get(name: String, defaultValue: String = "") = get(name) ?: defaultValue

    fun getOrThrow(name: String): String {
        return dotenv[name] ?: throw IllegalArgumentException("Missing environment variable $name")
    }

    fun getAll() = dotenv.entries().map { EnvVar(it.key, it.value) }

    private fun getPath(path: String) = Paths.get(path).toAbsolutePath().normalize()

    private fun envExists(path: Path) = Files.exists(path.resolve(".env")) || Files.exists(path.resolve(".env.dist"))

    private fun findEnvDirectory(): String {
        for (searchPath in searchPaths) {
            val path = getPath(searchPath)
            if (envExists(path)) return path.toString()
        }

        var path = getPath("./")
        do {
            if (envExists(path)) return path.toString()
            path = path.parent
        } while (path != null)
        return "./"
    }
}
