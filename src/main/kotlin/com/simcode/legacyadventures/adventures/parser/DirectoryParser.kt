package com.simcode.legacyadventures.adventures.parser

import java.nio.file.Path

interface DirectoryParser<T> {

    fun parse(directory: Path): T

}