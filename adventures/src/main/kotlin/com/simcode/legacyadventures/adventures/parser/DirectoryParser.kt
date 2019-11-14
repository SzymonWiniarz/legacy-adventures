package com.simcode.legacyadventures.adventures.parser

import java.nio.file.Path

internal interface DirectoryParser<T> {

    fun parse(directory: Path): T

}