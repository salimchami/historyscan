package io.sch.historyscan.infrastructure.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.web.filter.ForwardedHeaderFilter

@Configuration
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL])
class HateoasConfig : AsyncConfigurer {
    @Bean
    fun forwardedHeaderFilter(): FilterRegistrationBean<ForwardedHeaderFilter> {
        val bean = FilterRegistrationBean<ForwardedHeaderFilter>()
        bean.filter = ForwardedHeaderFilter()
        return bean
    }
}
