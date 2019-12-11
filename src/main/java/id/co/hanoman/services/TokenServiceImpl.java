package id.co.hanoman.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.hanoman.domain.Token;
import id.co.hanoman.repositories.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private TokenRepository tokenRepository;

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Iterable<Token> listAllProducts() {
        logger.debug("listAllTokens called");
        return tokenRepository.findAll();
    }

    @Override
    public Token getTokenById(Integer id) {
        logger.debug("getProductById called");
        return tokenRepository.findById(id).orElse(null);
    }

    @Override
    public Token getTokenByType(String type) {
        logger.debug("getProductById called");
        return tokenRepository.findByType(type);
    }

    @Override
    public Token saveToken(Token token) {
        logger.debug("saveToken called");
        return tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Integer id) {
        logger.debug("deleteProduct called");
        tokenRepository.deleteById(id);
    }
}
