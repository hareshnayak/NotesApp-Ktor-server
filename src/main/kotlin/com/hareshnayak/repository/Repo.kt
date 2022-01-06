package com.hareshnayak.repository

import com.hareshnayak.data.model.Note
import com.hareshnayak.data.model.User
import com.hareshnayak.data.table.NoteTable
import com.hareshnayak.data.table.UserTable
import com.hareshnayak.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

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

    suspend fun findUserByEmail(email:String) = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map { rowToUser(it) }
            .singleOrNull()
    }

    private fun rowToUser(row: ResultRow?):User?{
        if(row == null){
            return null
        }

        return User(
            email =  row[UserTable.email],
            hashPassword = row[UserTable.hashPassword],
            userName = row[UserTable.name]
        )
    }

    suspend fun addNote(note: Note, email:String){
        dbQuery{
            NoteTable.insert{ nt ->
                nt[id] = note.id
                nt[userEmail] = email
                nt[noteTitle] = note.noteTitle
                nt[description] = note.description
                nt[date] = note.date
            }
        }
    }


    suspend fun getAllNotes(email:String):List<Note> = dbQuery {

        NoteTable.select {
            NoteTable.userEmail.eq(email)
        }.mapNotNull { rowToNote(it) }

    }


    suspend fun updateNote(note:Note,email: String){
        dbQuery {
            NoteTable.update(
                where = {
                    NoteTable.userEmail.eq(email) and NoteTable.id.eq(note.id)
                }
            ){ nt->
                nt[noteTitle] = note.noteTitle
                nt[description] = note.description
                nt[date] = note.date
            }
        }
    }

    suspend fun deleteNote(id:String,email: String){
        dbQuery {
            NoteTable.deleteWhere {  NoteTable.userEmail.eq(email) and NoteTable.id.eq(id) }
        }
    }


    private fun rowToNote(row:ResultRow?): Note? {

        if(row == null){
            return null
        }

        return Note(
            id = row[NoteTable.id],
            noteTitle = row[NoteTable.noteTitle],
            description =  row[NoteTable.description],
            date = row[NoteTable.date]
        )

    }



}