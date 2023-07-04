package de.fhe.adoptapal.domain


data class UserEmailUniqueException(val email: String) : Exception("Email: $email already exists in the database")
