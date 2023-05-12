package com.BeatusL.Malinka

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3

interface Entity {
    fun create()

    fun move()

    fun shoot()

    fun collaps()
}

class Player: Entity {
    var rect = Rectangle()
    lateinit var image: Texture

    override fun create() {
        rect.x = (800 / 2 - 64 / 2).toFloat()
        rect.y = 20f
        image = Texture(Gdx.files.internal("data/bucket.png"))
    }

    override fun move() {
        if (Gdx.input.isTouched) {
            val touchPos = Vector3()
            touchPos[Gdx.input.x.toFloat(), Gdx.input.y.toFloat()] = 0f
            camera.unproject(touchPos)
            rect.x = touchPos.x - 64 / 2
        }
    }
}
