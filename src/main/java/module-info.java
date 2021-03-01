module name {
    requires static java.net.http;
    requires static jakarta.jakartaee.web.api;
    requires static microprofile.openapi.api;

    exports uk.me.mattgill.samples.word;
}