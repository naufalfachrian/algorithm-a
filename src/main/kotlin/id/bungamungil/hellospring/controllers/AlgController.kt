package id.bungamungil.hellospring.controllers

import id.bungamungil.hellospring.models.Score
import id.bungamungil.hellospring.module.AlgA
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AlgController {

    @PostMapping("/find-alg")
    fun find(@RequestBody score: Score): AlgA.Result {
        if (score.items == null) throw RuntimeException("score.items should not be null")
        return AlgA.calculate(score.items)
    }

}