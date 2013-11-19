package org.gametube.redisflexiblecache

class CacheUsageExampleController {

    @RedisFlexibleCache(key = 'indexAction', expire = '120', reAttachToSession = false)
    def index() {
        //this method will be cached properly because returns a map
        log.debug('not cached yet')
        [result: 'redis rocks']
    }

    @RedisFlexibleCache(key = 'annotation:#{id}', group = 'high', reAttachToSession = false)
    def indexWithParams(String id) {
        //this method will be cached properly because returns a map
        log.debug('not cached yet')
        [result: 'redis rocks with id: ' + id]
    }

    @RedisFlexibleCache(key = 'notWorking', expire = '120', reAttachToSession = false)
    def notWorking() {
        //this method will not be cached properly because has not return values
        log.debug('not cached yet')
        render 'ops'
    }

    def nowWorking() {
        //this will work
        def result = redisFlexibleCache group: 'mid', key: "now", reAttachToSession: true, {
            log.debug('not cached yet')
            [books: Book.list(),
                    totalPrice: 1000]
        }

        render "we have these books: ${result.books.name.join(",")}. total price: ${result.totalPrice}"
    }

    def redisFlexibleCacheService

    def alsoLikeThis() {
        // when group and ttl are both != null, only ttl will be used.
        def result = redisFlexibleCacheService.doCache("also", 'mid', 120, false, {
            log.debug('not cached yet')
            [books: Book.list(),
                    totalPrice: 1000]
        })
        render "we have these books: ${result.books.name.join(",")}. total price: ${result.totalPrice}"
    }

    @EvictRedisFlexibleCache(key = 'indexAction')
    def evictIndexAction() {
        evictRedisFlexibleCache key: 'indexAction'
        render 'evicted indexAction'
    }

    def evictAll() {
        redisFlexibleCacheService.evictCache('*', {
            log.debug('evict everything')
        })
        render 'evicted everything'
    }
}
