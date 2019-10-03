package com.example.watchedonnetflix.ImdbAPI

data class OMDbDTO(
    var Title : String,
    var Year : String,
    var Runtime : String,
    var Genre : String,
    var Country : String,
    var Poster : String,
    var Plot : String,
    var imdbRating : Float
    ) {
}