package com.VidaPlus.VitaValore.dto.auth;

public record ResponseDto(String name,String cnpj, String token) {



    @Override
    public String name() {
        return name;
    }

    @Override
    public String cnpj() {
        return cnpj;
    }

    @Override
    public String token() {
        return token;
    }

}


