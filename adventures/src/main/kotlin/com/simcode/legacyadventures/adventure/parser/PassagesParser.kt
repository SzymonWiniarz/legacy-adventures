package com.simcode.legacyadventures.adventure.parser

import com.simcode.legacyadventures.adventure.Passage
import com.simcode.legacyadventures.adventure.Passages
import com.simcode.legacyadventures.adventure.parser.exceptions.AdventuresParsingException
import java.nio.file.Path

private const val PASSAGES_FILE_NAME = "passages.csv"
private const val PASSAGES_COLUMNS_COUNT = 2

internal class PassagesParser(private val descriptionTextReader: DescriptionTextReader): DirectoryParser<Passages> {

    override fun parse(directory: Path): Passages {
        val descriptionText = descriptionTextReader.readDescriptionText(directory)
        val passages = readPassages(directory)

        return Passages(descriptionText, passages)
    }

    private fun readPassages(directory: Path): List<Passage> {
        val passagesFile = directory.resolve(PASSAGES_FILE_NAME).toFile()

        if (!passagesFile.exists()) {
            throw AdventuresParsingException("No $PASSAGES_FILE_NAME file in $directory")
        }

        return passagesFile
            .readLines()
            .map { parsePassageLine(it, directory) }
    }

    private fun parsePassageLine(line: String, directory: Path): Passage {
        val fields = line.split(',')

        if (fields.size != PASSAGES_COLUMNS_COUNT) {
            throw AdventuresParsingException("Unexpected line in $PASSAGES_FILE_NAME in $directory: '$line'")
        }

        return Passage(fields[0], fields[1])
    }

}