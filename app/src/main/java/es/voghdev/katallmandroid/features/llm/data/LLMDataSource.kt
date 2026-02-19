package es.voghdev.katallmandroid.features.llm.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LLMDataSource @Inject constructor() {
    fun getLLMs(): Flow<List<LLM>> = flow {
        emit(
            listOf(
                LLM(
                    name = "GPT-4o",
                    company = "OpenAI",
                    releaseDate = "May 2024",
                    description = "Multimodal flagship model with vision, audio, and text capabilities. Faster and cheaper than GPT-4 Turbo.",
                ),
                LLM(
                    name = "Claude 3.5 Sonnet",
                    company = "Anthropic",
                    releaseDate = "June 2024",
                    description = "High-performance model balancing intelligence and speed. Excels at coding, analysis, and creative tasks.",
                ),
                LLM(
                    name = "Gemini 1.5 Pro",
                    company = "Google",
                    releaseDate = "February 2024",
                    description = "Long-context model supporting up to 1 million tokens. Strong at multimodal reasoning and document understanding.",
                ),
                LLM(
                    name = "Llama 3 70B",
                    company = "Meta",
                    releaseDate = "April 2024",
                    description = "Open-source large language model. Competitive performance with proprietary models across benchmarks.",
                ),
                LLM(
                    name = "Mistral Large",
                    company = "Mistral AI",
                    releaseDate = "February 2024",
                    description = "Top-tier reasoning model with strong multilingual support. Natively fluent in English, French, Spanish, German, and Italian.",
                ),
            )
        )
    }

    fun getLLMByIndex(index: Int): Flow<LLM> = flow {
        val llms = listOf(
            LLM(
                name = "GPT-4o",
                company = "OpenAI",
                releaseDate = "May 2024",
                description = "Multimodal flagship model with vision, audio, and text capabilities. Faster and cheaper than GPT-4 Turbo.",
            ),
            LLM(
                name = "Claude 3.5 Sonnet",
                company = "Anthropic",
                releaseDate = "June 2024",
                description = "High-performance model balancing intelligence and speed. Excels at coding, analysis, and creative tasks.",
            ),
            LLM(
                name = "Gemini 1.5 Pro",
                company = "Google",
                releaseDate = "February 2024",
                description = "Long-context model supporting up to 1 million tokens. Strong at multimodal reasoning and document understanding.",
            ),
            LLM(
                name = "Llama 3 70B",
                company = "Meta",
                releaseDate = "April 2024",
                description = "Open-source large language model. Competitive performance with proprietary models across benchmarks.",
            ),
            LLM(
                name = "Mistral Large",
                company = "Mistral AI",
                releaseDate = "February 2024",
                description = "Top-tier reasoning model with strong multilingual support. Natively fluent in English, French, Spanish, German, and Italian.",
            ),
        )
        emit(llms[index])
    }
}
