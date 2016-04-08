(ns ttt-clj-java-server.api.server
  (:import com.javawebserver.app.serverBuilders.SimpleServerBuilder))

(defn new-server-builder []
  (SimpleServerBuilder.))

(defn add-route [route response server-builder]
  (.addRoute server-builder route response))

(defn get-routes [server-builder]
  (.getRoutes server-builder))

(defn get-server [port server-builder]
  (.getServer server-builder port))
