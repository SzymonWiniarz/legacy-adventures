package com.simcode.legacyadventures.adventure.parser

import java.nio.file.Path

internal interface DirectoryParser<T> {

    fun parse(directory: Path): T

}