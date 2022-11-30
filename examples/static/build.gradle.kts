import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.github.node-gradle.node") version "3.5.0"
}

node {
    download.set(true)
    version.set("18.12.1")
}

val demoHtmlInput = file("src/demo.html")
val demoHtmlOutput = file("dist/demo.min.html")

val installHtmlMinifier by tasks.registering(NpmTask::class) {
    args.addAll("install", "html-minifier", "--save-dev")
}

val minifyDemo by tasks.registering(NpxTask::class) {
    dependsOn(installHtmlMinifier)

    command.set("html-minifier")

    args.add("--collapse-boolean-attributes")
    args.add("--collapse-whitespace")
    args.add("--decode-entities")
    args.add("--minify-css")
    args.add("--minify-js")
    args.add("--remove-attribute-quotes")
    args.add("--remove-comments")
    args.add("--remove-empty-attributes")
    args.add("--remove-optional-tags")
    args.add("--remove-redundant-attributes")
    args.add("--remove-script-type-attributes")
    args.add("--remove-style-link-type-attributes")
    args.add("--remove-tag-whitespace")
    args.add("--sort-attributes")
    args.add("--sort-class-name")
    args.add("--use-short-doctype")

    // output
    args.addAll("-o", demoHtmlOutput.canonicalPath)
    outputs.file(demoHtmlOutput)

    // input
    args.add(demoHtmlInput.canonicalPath)
    inputs.file(demoHtmlInput)
}

val demoHtml: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(demoHtml.name, minifyDemo) {
        builtBy(minifyDemo)
    }
}