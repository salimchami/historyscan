package io.sch.historyscan.common

import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.charset.StandardCharsets

@Configuration
class UTF8Config : WebMvcConfigurer {
    override fun configureMessageConverters(converters: List<HttpMessageConverter<*>>) {
        converters.forEach { converter ->
            if (converter is MappingJackson2HttpMessageConverter) {
                converter.defaultCharset = StandardCharsets.UTF_8
            }
        }
    }
}
