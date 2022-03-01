package com.tapisdev.myapplication.model


class SharedVariable {

    companion object {
        val nilaiJawabBenarLevel1 = 20.0
        val nilaiJawabBenarLevel3 = 10.0
        val nilaiJawabBenarLevel4 = 10.0
        var activeSkorLevel1 = 0.0
        var activeSkorLevel2 = 0.0
        var activeSkorLevel3 = 0.0
        var activeSkorLevel4 = 0.0

        open fun resetScore(){
            activeSkorLevel1 = 0.0
            activeSkorLevel2 = 0.0
            activeSkorLevel3 = 0.0
            activeSkorLevel4 = 0.0
        }

    }


}