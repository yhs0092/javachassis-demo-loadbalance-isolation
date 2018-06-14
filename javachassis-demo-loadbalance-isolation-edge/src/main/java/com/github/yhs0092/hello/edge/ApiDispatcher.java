package com.github.yhs0092.hello.edge;

import java.util.Map;

import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.apache.servicecomb.edge.core.CompatiblePathVersionMapper;
import org.apache.servicecomb.edge.core.EdgeInvocation;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class ApiDispatcher extends AbstractEdgeDispatcher {
  private CompatiblePathVersionMapper versionMapper = new CompatiblePathVersionMapper();

  @Override
  public int getOrder() {
    return 10000;
  }

  @Override
  public void init(Router router) {
    String regex = "/api/([^/]+)/([^/]+)/(.*)";
    router.route().handler(CorsHandler.create("w3schools\\.com").allowedMethod(HttpMethod.GET));
    // for debug purpose, the file caching is disabled
    router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false));
    router.routeWithRegex(regex).handler(CookieHandler.create());
    router.routeWithRegex(regex).handler(createBodyHandler());
    router.routeWithRegex(regex).failureHandler(this::onFailure).handler(this::onRequest);
  }

  protected void onRequest(RoutingContext context) {
    Map<String, String> pathParams = context.pathParams();
    String microserviceName = pathParams.get("param0");
    String pathVersion = pathParams.get("param1");
    String path = "/" + pathParams.get("param2");

    EdgeInvocation edgeInvocation = new EdgeInvocation();
    edgeInvocation.setVersionRule(versionMapper.getOrCreate(pathVersion).getVersionRule());

    edgeInvocation.init(microserviceName, context, path, httpServerFilters);
    edgeInvocation.edgeInvoke();
  }
}
