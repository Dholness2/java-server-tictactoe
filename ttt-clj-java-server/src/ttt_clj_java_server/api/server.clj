(ns ttt-clj-java-server.api.server
  (:import com.javawebserver.app.serverBuilders.SimpleServerBuilder))

(def serverBuilder (SimpleServerBuilder.))

(defn add-route [route response]
  (.addRoute serverBuilder route response))

(defn get-routes []
  (.getRoutes serverBuilder))

(defn get-server [port]
  (.getServer serverBuilder port))
