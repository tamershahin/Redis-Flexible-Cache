package org.gametube.cache.redis

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import org.codehaus.groovy.transform.GroovyASTTransformationClass

/**
 */
@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(['org.gametube.cache.redis.ast.EvictRedisFlexibleCacheASTTransformation'])
@interface EvictRedisFlexibleCache {
    String key() default '';
}
