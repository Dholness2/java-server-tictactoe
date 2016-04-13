(ns ttt-clj-java-server.api.response-builder
  (:import com.javawebserver.app.responseBuilders.HttpResponseBuilder))

(defn new-response-builder []
  (HttpResponseBuilder.))

(defn add-status [response status]
  (.addStatus response status))

(defn add-header [response header header-value]
  (.addHeader response header header-value))

(defn add-body [response body]
  (.addBody response body))

(defn get-response [response]
  (.getResponse response))

