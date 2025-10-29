package com.example.peacenest.data.repository

import com.example.peacenest.data.models.Article

object ArticlesRepository {
    
    fun getArticles(): List<Article> {
        return listOf(
            Article(
                id = 1,
                title = "Recupera el control ante un ataque de ansiedad",
                intro = "Cuando experimentamos un ataque de ansiedad, es común sentir que perdemos el control del cuerpo y la mente. En este artículo conocerás cómo identificar los síntomas, actuar en el momento adecuado y aplicar estrategias que te ayuden a recuperar la calma de forma eficaz.",
                image = "imagen1",
                articleUrl = "https://centromedicoabc.com/revista-digital/recupera-tu-control-ante-un-ataque-de-ansiedad/"
            ),
            Article(
                id = 2,
                title = "Cómo mejorar el sueño cuando sufres de ansiedad",
                intro = "El insomnio y la ansiedad suelen estar profundamente conectados. Dormir mal puede empeorar los síntomas de ansiedad y viceversa. En este artículo descubrirás rutinas, hábitos y consejos prácticos para mejorar tu calidad de sueño y recuperar el equilibrio emocional.",
                image = "imagen2",
                articleUrl = "https://medlineplus.gov/spanish/ency/patientinstructions/000853.htm"
            ),
            Article(
                id = 3,
                title = "Cómo calmar tu mente con meditación guiada",
                intro = "Este video guiado te ayudará a calmar la mente, reducir el estrés y encontrar equilibrio emocional mediante técnicas de meditación accesibles para todos.",
                image = "imagen3",
                articleUrl = "https://www.youtube.com/watch?v=pDigD65kLpE"
            ),
            Article(
                id = 4,
                title = "Alimentación y ansiedad: cómo lo que comes puede ayudarte",
                intro = "Conoce cómo ciertos hábitos alimenticios pueden aumentar o disminuir tus niveles de ansiedad. En este artículo descubrirás consejos prácticos basados en evidencia.",
                image = "imagen4",
                articleUrl = "https://www.unobravo.com/es/blog/ansiedad-por-comer"
            )
        )
    }
}

