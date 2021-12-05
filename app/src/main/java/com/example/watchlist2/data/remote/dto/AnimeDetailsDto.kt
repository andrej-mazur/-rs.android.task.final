package com.example.watchlist2.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimeDetailsDto(
    @Json(name = "request_hash")
    val requestHash: String?,
    @Json(name = "request_cached")
    val requestCached: Boolean,
    @Json(name = "request_cache_expiry")
    val requestCacheExpiry: Int?,
    @Json(name = "mal_id")
    val malId: Long,
    @Json(name = "url")
    val url: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "trailer_url")
    val trailerUrl: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "title_english")
    val titleEnglish: String?,
    @Json(name = "title_japanese")
    val titleJapanese: String?,
    @Json(name = "title_synonyms")
    val titleSynonyms: List<String?>,
    @Json(name = "type")
    val type: String?,
    @Json(name = "source")
    val source: String?,
    @Json(name = "episodes")
    val episodes: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "airing")
    val airing: Boolean,
    @Json(name = "aired")
    val aired: Aired,
    @Json(name = "duration")
    val duration: String?,
    @Json(name = "rating")
    val rating: String?,
    @Json(name = "score")
    val score: Double?,
    @Json(name = "scored_by")
    val scoredBy: Int?,
    @Json(name = "rank")
    val rank: Int?,
    @Json(name = "popularity")
    val popularity: Int?,
    @Json(name = "members")
    val members: Int?,
    @Json(name = "favorites")
    val favorites: Int?,
    @Json(name = "synopsis")
    val synopsis: String?,
    @Json(name = "background")
    val background: String?,
    @Json(name = "premiered")
    val premiered: Any,
    @Json(name = "broadcast")
    val broadcast: Any,
    @Json(name = "related")
    val related: Related,
    @Json(name = "producers")
    val producers: List<Producer>,
    @Json(name = "licensors")
    val licensors: List<Licensor>,
    @Json(name = "studios")
    val studios: List<Studio>,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "explicit_genres")
    val explicitGenres: List<Any>,
    @Json(name = "demographics")
    val demographics: List<Any>,
    @Json(name = "themes")
    val themes: List<Theme>,
    @Json(name = "opening_themes")
    val openingThemes: List<String?>,
    @Json(name = "ending_themes")
    val endingThemes: List<String?>,
    @Json(name = "external_links")
    val externalLinks: List<ExternalLink>
) {
    @JsonClass(generateAdapter = true)
    data class Aired(
        @Json(name = "from")
        val from: String?,
        @Json(name = "to")
        val to: String?,
        @Json(name = "prop")
        val prop: Prop,
        @Json(name = "string")
        val string: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Prop(
            @Json(name = "from")
            val from: From,
            @Json(name = "to")
            val to: To
        ) {
            @JsonClass(generateAdapter = true)
            data class From(
                @Json(name = "day")
                val day: Int?,
                @Json(name = "month")
                val month: Int?,
                @Json(name = "year")
                val year: Int?
            )

            @JsonClass(generateAdapter = true)
            data class To(
                @Json(name = "day")
                val day: Int?,
                @Json(name = "month")
                val month: Int?,
                @Json(name = "year")
                val year: Int?
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Related(
        @Json(name = "Adaptation")
        val adaptation: List<Adaptation>,
        @Json(name = "Prequel")
        val prequel: List<Prequel>,
        @Json(name = "Side story")
        val sideStory: List<SideStory>,
        @Json(name = "Alternative version")
        val alternativeVersion: List<AlternativeVersion>
    ) {
        @JsonClass(generateAdapter = true)
        data class Adaptation(
            @Json(name = "mal_id")
            val malId: Int?,
            @Json(name = "type")
            val type: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "url")
            val url: String?
        )

        @JsonClass(generateAdapter = true)
        data class Prequel(
            @Json(name = "mal_id")
            val malId: Int?,
            @Json(name = "type")
            val type: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "url")
            val url: String?
        )

        @JsonClass(generateAdapter = true)
        data class SideStory(
            @Json(name = "mal_id")
            val malId: Int?,
            @Json(name = "type")
            val type: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "url")
            val url: String?
        )

        @JsonClass(generateAdapter = true)
        data class AlternativeVersion(
            @Json(name = "mal_id")
            val malId: Int?,
            @Json(name = "type")
            val type: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "url")
            val url: String?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Producer(
        @Json(name = "mal_id")
        val malId: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )

    @JsonClass(generateAdapter = true)
    data class Licensor(
        @Json(name = "mal_id")
        val malId: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )

    @JsonClass(generateAdapter = true)
    data class Studio(
        @Json(name = "mal_id")
        val malId: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "mal_id")
        val malId: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )

    @JsonClass(generateAdapter = true)
    data class Theme(
        @Json(name = "mal_id")
        val malId: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )

    @JsonClass(generateAdapter = true)
    data class ExternalLink(
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?
    )
}