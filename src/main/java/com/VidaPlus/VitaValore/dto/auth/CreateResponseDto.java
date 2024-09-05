package com.VidaPlus.VitaValore.dto.auth;

public record CreateResponseDto(
        String name,
        String cnpj,
        String email,
        String password,
        String token
) {

    @Override
    public String name() {
        return name;
    }

    @Override
    public String cnpj() {
        return cnpj;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String token() {
        return token;
    }
}
