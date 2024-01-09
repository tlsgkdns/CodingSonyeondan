package com.example.codingsonyeondan.infra.exception

class UniqueAttributeAlreadyExistException (val modelName: String, val value: String, val attribute: String): RuntimeException(
    "$modelName's attribute $attribute $value is already exist"
)