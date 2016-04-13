(ns ttt-clj-java-server.api.server
  (:import com.javawebserver.app.builds.BasicBuild)
  (:import com.javawebserver.app.serverBuilders.SimpleServerBuilder)
  (:import com.javawebserver.app.Server))

(defn new-server-build []
  (BasicBuild. (SimpleServerBuilder.)))

(defn add-route [route response server-build]
  (.addRoute server-build route response))

(defn get-routes [server-build]
  (.getRoutes server-build))

(defn get-server [port server-build]
  (.getServer server-build port))

(defn serveron? [server]
  (.isServerOn server))

(defn server-off [server]
  (.off server))
