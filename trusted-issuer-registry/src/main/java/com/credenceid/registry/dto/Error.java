package com.credenceid.registry.dto;


/**
 * Error DTO used in API responses
 *
 * @param errorCode
 * @param errorMessage
 */
public record Error(String errorCode, String errorMessage) {
}
