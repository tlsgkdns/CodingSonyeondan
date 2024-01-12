package com.example.codingsonyeondan.infra.exception

class NotHaveAuthorityException (val model: String): RuntimeException("Don't have authority to access: $model")