package com.devhighlevel.plugins

import com.devhighlevel.clients.dynamo.DynamoClient
import com.devhighlevel.dao.UserDao
import com.devhighlevel.service.UserService
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient
import software.amazon.awssdk.http.nio.netty.NettySdkAsyncHttpService


object ModuleLoader {

    fun init(){
        startKoin {
            modules(
                listOf(
                    clientsModule(),
                    dataAccessModule(),
                    serviceModule()
                )
            )
        }
    }
}

fun clientsModule(): Module {
    return module {
        single { NettySdkAsyncHttpService() }
        single { DynamoClient( get()) }
    }
}

fun dataAccessModule(): Module {
    return module {
        single { UserDao( get() ) }
    }
}

fun serviceModule(): Module {
    return module {
        single { UserService( get() ) }
    }
}

