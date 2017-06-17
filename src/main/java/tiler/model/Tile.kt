package tiler.model

/**
 * Represents tile to draw.
 * Contains a path to its picture and its name (commonly picture name).
 */
data class Tile(val sourceImageUrl: SourceImageUrl, val name: String)