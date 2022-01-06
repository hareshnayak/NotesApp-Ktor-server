package com.hareshnayak.repository

import com.hareshnayak.data.model.User
import com.hareshnayak.data.table.UserTable
import com.hareshnayak.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert

class Repo {
    suspend fun addUser(user:User){
        dbQuery{
            UserTable.insert{ ut->
                ut[email] = user.email
                ut[hashPassword] = user.hashPassword
                ut[name] = user.userName
            }
        }
    }

}