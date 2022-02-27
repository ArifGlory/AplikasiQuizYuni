package com.tapisdev.myapplication.model


class SharedVariable {

    companion object {
        val nilaiJawabBenarLevel1 = 20.0
        var activeSkorLevel1 = 0.0

        open fun resetScore(){
            activeSkorLevel1 = 0.0
        }

    }


}