package com.simcode.legacyadventures.adventure.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths

internal class AdventureParserTest {

    private lateinit var adventureParser: AdventureParser

    @org.junit.jupiter.api.BeforeEach
    internal fun setUp() {
        adventureParser = AdventureParser.create()
    }

    @Test
    internal fun `should properly parse sample adventure root directory`() {
        val sampleAdventureDirectory = Paths.get(javaClass.getResource("/adventures/sample_adventure")?.toURI() ?: throw IllegalStateException("Cannot load sample_adventure"))

        val adventure = adventureParser.parse(sampleAdventureDirectory)

        assertThat(adventure.description).isEqualTo("""This is a sample adventure. It shows the directories structure rquired by adventures, and also is used for testing and development purposes.
                                                      |And of course it is happening long time ago, far far away...""".trimMargin())

        assertThat(adventure.locations).size().isEqualTo(3)
        assertThat(adventure.locations.keys).containsExactlyInAnyOrder("corridor_left", "corridor_right", "starting_hall")

    }

    @Test
    internal fun `should properly parse corridor_left location`() {
        val sampleAdventureDirectory = Paths.get(javaClass.getResource("/adventures/sample_adventure")?.toURI() ?: throw IllegalStateException("Cannot load sample_adventure"))

        val adventure = adventureParser.parse(sampleAdventureDirectory)

        val corridorLeftLocation = adventure.locations["corridor_left"]

        assertThat(corridorLeftLocation).isNotNull
        assertThat(corridorLeftLocation!!.description).isEqualTo("You are in very dark corridor. You are not sure if it even leads somewhere, or is it a dead end.")

        assertThat(corridorLeftLocation.passages.description).isEqualTo("The only way out from this corridor you're able to see is the way back to the previous hall.")
        assertThat(corridorLeftLocation.passages.passages).size().isEqualTo(1)

        val passageToStartingHall = corridorLeftLocation.passages.passages[0]
        assertThat(passageToStartingHall.label).isEqualTo("back to hall")
        assertThat(passageToStartingHall.targetLocationId).isEqualTo("starting_hall")
    }

    @Test
    internal fun `should properly parse corridor_right location`() {
        val sampleAdventureDirectory = Paths.get(javaClass.getResource("/adventures/sample_adventure")?.toURI() ?: throw IllegalStateException("Cannot load sample_adventure"))

        val adventure = adventureParser.parse(sampleAdventureDirectory)

        val corridorRightLocation = adventure.locations["corridor_right"]

        assertThat(corridorRightLocation).isNotNull
        assertThat(corridorRightLocation!!.description).isEqualTo("This corridor smells good. Like it were some fresh fruits somewhere. It's quite bright there, however you still cannot see any fruits.")

        assertThat(corridorRightLocation.passages.description).isEqualTo("The only way out from this corridor you're able to see is the way back to the previous hall.")
        assertThat(corridorRightLocation.passages.passages).size().isEqualTo(1)

        val passageToStartingHall = corridorRightLocation.passages.passages[0]
        assertThat(passageToStartingHall.label).isEqualTo("back to hall")
        assertThat(passageToStartingHall.targetLocationId).isEqualTo("starting_hall")
    }

    @Test
    internal fun `should properly parse starting_hall location`() {
        val sampleAdventureDirectory = Paths.get(javaClass.getResource("/adventures/sample_adventure")?.toURI() ?: throw IllegalStateException("Cannot load sample_adventure"))

        val adventure = adventureParser.parse(sampleAdventureDirectory)

        val startingHallLocation = adventure.locations["starting_hall"]

        assertThat(startingHallLocation).isNotNull
        assertThat(startingHallLocation!!.description).isEqualTo("You stand alone in a large, old hall. It's quite dark and cold there. You barely see anything.")

        assertThat(startingHallLocation.passages.description).isEqualTo("After a few minutes of exploring the hall, you see that there are doors both on the left and on the right hand side.")
        assertThat(startingHallLocation.passages.passages).size().isEqualTo(2)

        val passageToLeftCorridor = startingHallLocation.passages.passages[0]
        assertThat(passageToLeftCorridor.label).isEqualTo("left door")
        assertThat(passageToLeftCorridor.targetLocationId).isEqualTo("corridor_left")

        val passageToRightCorridor = startingHallLocation.passages.passages[1]
        assertThat(passageToRightCorridor.label).isEqualTo("right door")
        assertThat(passageToRightCorridor.targetLocationId).isEqualTo("corridor_right")
    }
}