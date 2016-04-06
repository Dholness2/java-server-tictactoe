(ns ttt-clj-java-server.responses.new-game-response
  (:require [ttt-clj-java-server.api.response-builder :refer :all])
  (:import com.javawebserver.app.responses.Response))

(def new-board "_________")
(def index "views/index.html")

(defn get-body [path]
  (let [body (.getBytes (str (slurp path)))]
    body))

(defn build-response-message [body-bytes]
  (let [response (new-response-builder)]
    (add-status response "OK")
    (add-header response "Content-length : " (str (count body-bytes)))
    (add-body response body-bytes)
    response))

(defn new-game []
  (reify Response
    (handleRequest [this request]
      (let [body (get-body index)
            response (build-response-message body)]
        (get-response response)))))
