package myccopcycle.security.oauth2;

import java.util.Collection;
import myccopcycle.security.SecurityUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public JwtGrantedAuthorityConverter() {
        // Bean extracting authority.
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return SecurityUtils.extractAuthorityFromClaims(jwt.getClaims());
    }
}
