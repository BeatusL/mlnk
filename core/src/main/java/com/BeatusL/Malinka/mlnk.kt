package com.BeatusL.Malinka


import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.TimeUtils


class Malinka : ApplicationAdapter() {
    private lateinit var bucketImage: Texture
    private lateinit var dropImage: Texture
    private lateinit var dropSound: Sound
    private lateinit var music: Music
    private lateinit var batch: SpriteBatch
    private var bucket = Rectangle()
    private val camera = OrthographicCamera()
    private val raindrops by lazy { mutableListOf<Rectangle>() }
    private var lastDropTime: Long = 0


    override fun create() {
        bucketImage = Texture(Gdx.files.internal("data/bucket.png"))
        dropImage = Texture(Gdx.files.internal("data/drop.png"))
        dropSound = Gdx.audio.newSound(Gdx.files.internal("data//drop.wav"))
        music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"))
        batch = SpriteBatch()
        camera.setToOrtho(false, 800f, 480f)
        spawnDrop()
        bucket.x = (800 / 2 - 64 / 2).toFloat()
        bucket.y = 20f
        bucket.width = 64f
        bucket.height = 64f

        music.isLooping = true
        music.play()
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0.2f, 1f)
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(bucketImage, bucket.x, bucket.y)
        batch.end()
        if (Gdx.input.isTouched) {
            val touchPos = Vector3()
            touchPos[Gdx.input.x.toFloat(), Gdx.input.y.toFloat()] = 0f
            camera.unproject(touchPos)
            bucket.x = touchPos.x - 64 / 2
        }
        if (bucket.x < 0) bucket.x = 0f
        if (bucket.x > 800 - 64) bucket.x = 800 - 64f
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnDrop()
        val iter = raindrops.iterator()
        while (iter.hasNext()) {
            val raindrop = iter.next()
            raindrop.y -= 200 * Gdx.graphics.deltaTime
            if (raindrop.y + 64 < 0) iter.remove()
            if (raindrop.overlaps(bucket)) {
                dropSound.play()
                iter.remove()
            }

        }

        batch.begin()
        batch.draw(bucketImage, bucket.x, bucket.y)
        for (raindrop in raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y)
        }
        batch.end()

    }

    private fun spawnDrop() {
        val raindrop = Rectangle()
        raindrop.x = MathUtils.random(0, 800 - 64).toFloat()
        raindrop.y = 480f
        raindrop.width = 64f
        raindrop.height = 64f
        raindrops.add(raindrop)
        lastDropTime = TimeUtils.nanoTime()
    }

    override fun dispose() {
        dropImage.dispose()
        bucketImage.dispose()
        dropSound.dispose()
        music.dispose()
        batch.dispose()    }

}


