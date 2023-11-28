package com.ben.controller

import com.ben.InterviewService
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.annotation.Serdeable.Deserializable
import jakarta.inject.Inject

@Introspected
@Deserializable
@Serdeable
data class Candidate(val name: String)

@Controller("/interview")
class InterviewController(
    @Inject
    private val interviewService: InterviewService
) {
    @Post("/vote")
    fun vote(@Body candidate: Candidate): Candidate {
        interviewService.vote(candidate.name)
        return candidate
    }
}