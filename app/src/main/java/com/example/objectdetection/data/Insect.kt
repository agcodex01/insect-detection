package com.example.objectdetection.data

import com.example.objectdetection.R

data class Insect(
    val name: String,
    val description: String,
//    val imageId: Int
) {
    fun matchQuery(query: String): Boolean {
        return "$name$description".lowercase().contains(query)
    }
}


val supportedInsects = listOf(
    Insect(
        name = "Mole Cricket",
        description = "The insects is light brown; its wings are folded and do not cover the full length of the abdomen. The forelegs are broad, curved, with strong  teeth like structures for digging soil. The hind legs, eyes, and antennae are small and almost invisible. The adult is  25-35 millimeters (mm) long",
//        imageId = R.raw.ant
    ),
    Insect(
        name = "Leaf folder",
        description = "The adults are light brown moths with a wingspan of 12-20mm. A dark terminal band characterizes the outer margins of the wings. Larvae are transparent green and measure up to 2.5 centimeters (cm in length. Adults are attracted to light at night; daytime, they are mostly found in shaded or grassy areas",
//        imageId = R.raw.ant
    ),
    Insect(
        name = "Case Bearer",
        description = "The adults are small, delicate, snowy-white moths with pale brown or black spots on the wings. The y have a wingspan of 15-25mm. Larvae are pale translucent green, with a pale orange head. There are easy early detection methods to know the pest activity in the area. Moths are attracted to light traps",
//        imageId = R.raw.ant
    ),
    Insect(
        name = "Skipper",
        description = "The adult is a stout brown butterfly with small white spots on the wings. Adults are active during the day, making darting, erratic movements from which the name skipper was coined. Adults rest in the shade during the day. Larvae are elongated with a constriction behind the head accentuating their flattened shape.",
//        imageId = R.raw.ant
    ),
    Insect(
        name = "Locust",
        description = "The adults are small, yellow and brown, about 3cm in body length, with conspicuous, broad, brown stripes running laterally through the eyes a extending posteriorly along the wings. The antennae are short, much less than the length of the body. When in swarms, they are in their migratory phase and are called locusts.\n"
    ),
    Insect(
        name = "Army Worm",
        description = "The adult moth has dark purplish brown forewings  with numerous spots and light-colored lines. The hind wings are whitish, narrowly banded along the outer margin. The wingspan is about 30mm. Larvae are soil-dwelling or hide underground to avoid predation by birds. They become active at night and emerge from the ground. Larvae come in various colors, ranging from creamy white to."
    ),
    Insect(
        name = "Whorl Maggot",
        description = "Adult flies are dull grey. Females are 1.8mm-2.3mm long. Males are slightly shorter."
    ),
    Insect(
        name = "Yellow Stem Borer",
        description = "The male and female adults are two different forms, and with distinct sexual characteristics. The female moth has one dark spot at the center of its bright yellowish forewings, while spots on the   forewings of the male are not clearly seen. The wingspan is 22mm-30mm. The males are smaller than the females, and do not have yellowish hairs at the end of the abdomen."
    ),
    Insect(
        name = "White Stem Borer",
        description = "The adult of this species looks similar to the YSB except that it does not have any dark spot on the forewings, in either sex. Hence, the WSB sexes look similar. Orange hairs are present at the end of the female"
    ),
    Insect(
        name = "Pink Stem Borer",
        description = "The adult moth is fawn-colored with brown streaks on the forewings and white hind wings. There is a cluster of hairs on the neck. The wingspan of female moths is 3mm0-35mm. The male and female moths can be distinguished by their antennae; they are comb like in the male and threadlike in the female. The males are smaller than the females. The larva is purplish pink on the back and white on the abdomen. The head capsule is orange- red. The body is distinctly segmented with no stripes, and tapers toward the abdominal tips. Larvae are found at ground level inside "
    ),
)