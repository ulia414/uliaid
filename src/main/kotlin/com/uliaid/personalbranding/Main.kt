package com.uliaid.personalbranding

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val profile = PersonalBrandingProfile.default()

    val server = HttpServer.create(InetSocketAddress(port), 0)
    server.createContext("/") { exchange ->
        respondHtml(exchange, renderLandingPage(profile))
    }
    server.executor = null

    println("Personal branding web app berjalan di http://localhost:$port")
    server.start()
}

private fun respondHtml(exchange: HttpExchange, html: String) {
    val bytes = html.toByteArray(Charsets.UTF_8)
    exchange.responseHeaders.add("Content-Type", "text/html; charset=utf-8")
    exchange.sendResponseHeaders(200, bytes.size.toLong())
    exchange.responseBody.use { it.write(bytes) }
}

data class PersonalBrandingProfile(
    val name: String,
    val title: String,
    val tagline: String,
    val about: String,
    val strengths: List<String>,
    val focusAreas: List<String>,
    val ctaLabel: String,
    val ctaLink: String,
    val email: String,
    val github: String
) {
    companion object {
        fun default(): PersonalBrandingProfile = PersonalBrandingProfile(
            name = "Uli Aid",
            title = "Personal Brand Website",
            tagline = "Membangun solusi digital yang rapi, cepat, dan berdampak.",
            about = "Saya dikenal sebagai pribadi yang inisiatif, senang belajar hal baru, dan fokus menyelesaikan masalah dengan pendekatan praktis.",
            strengths = listOf(
                "Problem solving terstruktur",
                "Komunikasi jelas dan kolaboratif",
                "Eksekusi cepat dengan standar kualitas"
            ),
            focusAreas = listOf(
                "Pengembangan Web",
                "Automasi Workflow",
                "Productivity System"
            ),
            ctaLabel = "Hubungi Saya",
            ctaLink = "mailto:hello@uliaid.dev",
            email = "hello@uliaid.dev",
            github = "https://github.com/uliaid"
        )
    }
}

fun renderLandingPage(profile: PersonalBrandingProfile): String = """
<!doctype html>
<html lang="id">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>${profile.name} - ${profile.title}</title>
  <style>
    :root {
      --bg: #0b1020;
      --card: #121a33;
      --text: #e8eeff;
      --muted: #a9b5d6;
      --accent: #63a4ff;
    }
    * { box-sizing: border-box; }
    body {
      margin: 0;
      font-family: Inter, system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif;
      background: radial-gradient(1200px 800px at 85% -10%, #1c2d5a 0%, var(--bg) 50%);
      color: var(--text);
      line-height: 1.6;
    }
    .container { max-width: 980px; margin: 0 auto; padding: 24px; }
    .hero {
      display: grid;
      gap: 16px;
      padding: 64px 0 24px;
    }
    .pill {
      display: inline-block;
      width: fit-content;
      border: 1px solid #2d3f72;
      color: var(--muted);
      border-radius: 999px;
      padding: 6px 14px;
      font-size: 14px;
    }
    h1 { margin: 0; font-size: clamp(32px, 6vw, 56px); line-height: 1.1; }
    .tagline { font-size: 20px; color: var(--muted); max-width: 700px; }
    .cta {
      width: fit-content;
      margin-top: 8px;
      text-decoration: none;
      color: #03112e;
      background: var(--accent);
      font-weight: 700;
      border-radius: 10px;
      padding: 12px 18px;
    }
    .grid {
      display: grid;
      gap: 16px;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      margin: 24px 0 40px;
    }
    .card {
      background: rgba(18, 26, 51, .8);
      border: 1px solid #2a3766;
      border-radius: 14px;
      padding: 18px;
      backdrop-filter: blur(2px);
    }
    h2 { margin-top: 0; font-size: 20px; }
    ul { margin: 0; padding-left: 18px; }
    footer { border-top: 1px solid #223057; padding: 20px 0 40px; color: var(--muted); }
    a { color: #9bc2ff; }
  </style>
</head>
<body>
  <main class="container">
    <section class="hero">
      <span class="pill">Halo, saya ${profile.name}</span>
      <h1>${profile.title}</h1>
      <p class="tagline">${profile.tagline}</p>
      <a class="cta" href="${profile.ctaLink}">${profile.ctaLabel}</a>
    </section>

    <section class="grid">
      <article class="card">
        <h2>Tentang Saya</h2>
        <p>${profile.about}</p>
      </article>
      <article class="card">
        <h2>Kekuatan Utama</h2>
        <ul>
          ${profile.strengths.joinToString("\n") { "<li>$it</li>" }}
        </ul>
      </article>
      <article class="card">
        <h2>Fokus Saat Ini</h2>
        <ul>
          ${profile.focusAreas.joinToString("\n") { "<li>$it</li>" }}
        </ul>
      </article>
    </section>

    <footer>
      <p>Email: <a href="mailto:${profile.email}">${profile.email}</a></p>
      <p>GitHub: <a href="${profile.github}" target="_blank" rel="noreferrer">${profile.github}</a></p>
      <small>Tip: ubah data profil di <code>PersonalBrandingProfile.default()</code> agar sesuai branding personalmu.</small>
    </footer>
  </main>
</body>
</html>
""".trimIndent()
