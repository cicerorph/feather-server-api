import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.task.NodeTask

plugins {
    id("com.github.node-gradle.node") version "3.5.0"
}

node {
    download.set(true)
    version.set("18.12.1")
    npmInstallCommand.set("install")
}

val shopHtmlInput = file("src/shop.html")
val shopHtmlOutput = file("dist/shop.min.html")

val installHtmlMinifier by tasks.registering(NpmTask::class) {
    args.addAll("install", "html-minifier", "--save-dev")
}

val minifyShopHtml by tasks.registering(NodeTask::class) {
    dependsOn(installHtmlMinifier)

    script.set(file("node_modules/html-minifier/cli.js"))

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
    args.addAll("-o", shopHtmlOutput.canonicalPath)
    outputs.file(shopHtmlOutput)

    // input
    args.add(shopHtmlInput.canonicalPath)
    inputs.file(shopHtmlInput)
}

val shopHtml: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(shopHtml.name, minifyShopHtml) {
        builtBy(minifyShopHtml)
    }
}