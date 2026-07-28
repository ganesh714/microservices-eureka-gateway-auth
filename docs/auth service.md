User entity(AppUser): a model which is to strore the user details who login (like username,password,name...)
AppUserDetails implements UserDetails -> Override methods of UserDetails (getauthorities()...),
    this is used by spring boot like format to give hte user details from AppUser model

AppUserDetailsService : for " AppUserDetails loadUserByUsername()" which will be called by spring security(security config) to check a login, when it get request to login it will fetch AppUserDetails model with that details

Security Config:
    bean - SecurityFilterChain getSec...(HttpSecurity   ..) 
        it can modifies filchain order or add any thing to that chain
        declare non secure endpoints like /login, /register ...
    
    to declare beans like
    AuthenticationProvider
    AuthenticationManager
    PasswordEncoder
    RoleHierarchy

JwtService:
    loadPublicKey() 
        returns public key from the file path given
        removes "--beginprivatekey--" and "--endpri..." = key
        keyBytes = decoder.decode(key)
        keyFactory("RSA" or hsa) .generatePrivate(keyspec gen from key bytes)
    loadPrivateKey()
        similar to public key method
    
    generateToken(AppUserDetails user)
        create claims from user details
        Jwts.builder.claims.subject.issuedAt.expiration.signwith(privatekey).compact

    extractClaims(jwtToken, Function<Claims, T> typeOfClaim)
        Jwts.parser.verifywith(publicKey).build...

    extractUserName(token)
        extractClaims(jwtToken, Claims::getSubject)
    isTokenExpired(token)

    ValidateToken(token, userDetails)
        token not expired,
        userDetails

JwtFilter:
    This is a filter (OncePerRequestFilter) that intercepts every single HTTP request before it ever reaches your controllers.
    doFilterInternal(.....):
        verify token and tell the filterChain to continue your process


----

Register:
    request from client(username,password)
    Dev controller
    Dev Ser -> encode password
    save to DB

Login:
    request from client(username,password)
    Dev controller
    Dev Ser -> generate Token()
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        BG - Spring Security calls your DeveloperUserDetailsService to fetch the hashed password from the database, runs BCrypt math to see if "myPassword123" matches the hash, and verifies the user.
        if success -> JwtService.generateToken(user)
        return  the token

Authed Endpoint(like GET /getUsername | you will attach Authorization: Bearer <token>):
Before COntroller Toched:
    - JwtFilter.doFilterInternal() intercepts/caches req
    - From Authorization header, sees "Bearer ", and extracts raw JWT token.
    - JwtService.extractUserName(jwtToken) returns "ganesh".
    - It fetches "ganesh" from the database and asks JwtService.validateToken(): "Is this token expired or fake?"
    ->> If the token is 100% valid, JwtFilter creates an authentication object and stamps it into Spring Security's memory for this request:
    SecurityContextHolder.getContext().setAuthentication(UPAToken);
(This tells Spring Security: "Stop checking! This user is authenticated as 'ganesh' with ROLE_ADMIN for this request!")
filterChain.doFilter(request, response) - continue with other filters and go to controllers or net steps

------

for miroservices
 there is a spetial end point required sometimes that is 
 "/validate" endpoint(which will use bygateway or other microservices) :  check whether the token got expired or not