package com.task.testtask_20_11;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * These annotations can be uncommented to remove Warning "For a stable JSON structure, please use
 * Spring Data's PagedModel (globally via @EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO))",
 * but Pageable object is less informative then
 */
//@Configuration
//@EnableSpringDataWebSupport(
//        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
//)
public class JacksonConfig {
}

