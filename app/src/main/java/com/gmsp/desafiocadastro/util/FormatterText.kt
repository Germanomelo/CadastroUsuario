package com.gmsp.desafiocadastro.util

fun formatterText(text: String, mask: String): String {
    val textFormatted = StringBuilder()
    var indexText = 0
    var indexMask = 0

    while (indexText < text.length && indexMask < mask.length) {

        if (mask[indexMask] == '#') {
            textFormatted.append(text[indexText])
            indexText++
        } else {
            textFormatted.append(mask[indexMask])
        }
        indexMask++
    }

    return textFormatted.toString()
}