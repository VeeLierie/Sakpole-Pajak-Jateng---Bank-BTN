package id.co.hanoman.services;


import id.co.hanoman.domain.Token;

public interface TokenService {
    Iterable<Token> listAllProducts();

    Token getTokenById(Integer id);

    Token getTokenByType(String type);

    Token saveToken(Token token);

    void deleteToken(Integer id);
}
