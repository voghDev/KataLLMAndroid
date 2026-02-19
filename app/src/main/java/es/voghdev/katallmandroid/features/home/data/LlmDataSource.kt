package es.voghdev.katallmandroid.features.home.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LlmDataSource @Inject constructor() {
    fun getLlms(): Flow<List<Llm>> = flow {
        emit(
            listOf(
                Llm(
                    name = "Claude Sonnet 4",
                    company = "Anthropic",
                    releaseDate = "2025-05",
                    description = "Anthropic's balanced model offering strong performance across coding, analysis, and creative tasks with fast response times.",
                ),
                Llm(
                    name = "Claude Opus 4",
                    company = "Anthropic",
                    releaseDate = "2025-05",
                    description = "Anthropic's most capable model, excelling at complex reasoning, research, and nuanced content generation.",
                ),
                Llm(
                    name = "Gemini 2.5 Pro",
                    company = "Google DeepMind",
                    releaseDate = "2025-03",
                    description = "Google's advanced multimodal model with a large context window and strong reasoning capabilities.",
                ),
                Llm(
                    name = "o1",
                    company = "OpenAI",
                    releaseDate = "2024-12",
                    description = "OpenAI's reasoning-focused model that uses chain-of-thought to solve complex math, science, and coding problems.",
                ),
                Llm(
                    name = "GPT-4o",
                    company = "OpenAI",
                    releaseDate = "2024-05",
                    description = "OpenAI's versatile multimodal model supporting text, vision, and audio with fast inference.",
                ),
                Llm(
                    name = "GPT-5",
                    company = "OpenAI",
                    releaseDate = "2025-06",
                    description = "OpenAI's next-generation flagship model with improved reasoning and broader knowledge capabilities.",
                ),
                Llm(
                    name = "Llama 4 Maverick",
                    company = "Meta",
                    releaseDate = "2025-04",
                    description = "Meta's open-weight mixture-of-experts model offering strong multilingual and coding performance.",
                ),
            )
        )
    }
}
