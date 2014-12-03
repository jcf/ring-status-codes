(ns ring.util.status-code
  (:refer-clojure :exclude [keys])
  (:require [clojure.string :as str]))

(def store
  #{{:code 100, :key :continue, :desc "Continue"}
    {:code 101, :key :switching-protocols, :desc "Switching Protocols"}
    {:code 102, :key :processing, :desc "Processing"}
    {:code 200, :key :ok, :desc "OK"}
    {:code 201, :key :created, :desc "Created"}
    {:code 202, :key :accepted, :desc "Accepted"}
    {:code 203, :key :non-authoritative-information, :desc "Non-Authoritative Information"}
    {:code 204, :key :no-content, :desc "No Content"}
    {:code 205, :key :reset-content, :desc "Reset Content"}
    {:code 206, :key :partial-content, :desc "Partial Content"}
    {:code 207, :key :multi-status, :desc "Multi-Status"}
    {:code 208, :key :already-reported, :desc "Already Reported"}
    {:code 226, :key :im-used, :desc "IM Used"}
    {:code 300, :key :multiple-choices, :desc "Multiple Choices"}
    {:code 301, :key :moved-permanently, :desc "Moved Permanently"}
    {:code 302, :key :found, :desc "Found"}
    {:code 303, :key :see-other, :desc "See Other"}
    {:code 304, :key :not-modified, :desc "Not Modified"}
    {:code 305, :key :use-proxy, :desc "Use Proxy"}
    {:code 306, :key :reserved, :desc "Reserved"}
    {:code 307, :key :temporary-redirect, :desc "Temporary Redirect"}
    {:code 308, :key :permanent-redirect, :desc "Permanent Redirect"}
    {:code 400, :key :bad-request, :desc "Bad Request"}
    {:code 401, :key :unauthorized, :desc "Unauthorized"}
    {:code 402, :key :payment-required, :desc "Payment Required"}
    {:code 403, :key :forbidden, :desc "Forbidden"}
    {:code 404, :key :not-found, :desc "Not Found"}
    {:code 405, :key :method-not-allowed, :desc "Method Not Allowed"}
    {:code 406, :key :not-acceptable, :desc "Not Acceptable"}
    {:code 407, :key :proxy-authentication-required, :desc "Proxy Authentication Required"}
    {:code 408, :key :request-timeout, :desc "Request Timeout"}
    {:code 409, :key :conflict, :desc "Conflict"}
    {:code 410, :key :gone, :desc "Gone"}
    {:code 411, :key :length-required, :desc "Length Required"}
    {:code 412, :key :precondition-failed, :desc "Precondition Failed"}
    {:code 413, :key :request-entity-too-large, :desc "Request Entity Too Large"}
    {:code 414, :key :request-uri-too-long, :desc "Request-URI Too Long"}
    {:code 415, :key :unsupported-media-type,, :desc "Unsupported Media Type"}
    {:code 416, :key :requested-range-not-satisfiable, :desc "Requested Range Not Satisfiable"}
    {:code 417, :key :expectation-failed, :desc "Expectation Failed"}
    {:code 422, :key :unprocessable-entity, :desc "Unprocessable Entity"}
    {:code 423, :key :locked, :desc "Locked"}
    {:code 424, :key :failed-dependency, :desc "Failed Dependency"}
    {:code 426, :key :upgrade-required, :desc "Upgrade Required"}
    {:code 428, :key :precondition-required, :desc "Precondition Required"}
    {:code 429, :key :too-many-requests, :desc "Too Many Requests"}
    {:code 431, :key :request-header-fields-too-large, :desc "Request Header Fields Too Large"}
    {:code 500, :key :internal-server-error, :desc "Internal Server Error"}
    {:code 501, :key :not-implemented, :desc "Not Implemented"}
    {:code 502, :key :bad-gateway, :desc "Bad Gateway"}
    {:code 503, :key :service-unavailable, :desc "Service Unavailable"}
    {:code 504, :key :gateway-timeout, :desc "Gateway Timeout"}
    {:code 505, :key :http-version-not-supported, :desc "HTTP Version Not Supported"}
    {:code 506, :key :variant-also-negotiates-experimental, :desc "Variant Also Negotiates (Experimental)"}
    {:code 507, :key :insufficient-storage, :desc "Insufficient Storage"}
    {:code 508, :key :loop-detected, :desc "Loop Detected"}
    {:code 510, :key :not-extended, :desc "Not Extended"}
    {:code 511, :key :network-authentication-required, :desc "Network Authentication Required"}})

(defn- lookup-kv [k v]
  (first (filter #(= (k %) v) store)))

(def ^:private lookup-code (partial lookup-kv :code))
(def ^:private lookup-key  (partial lookup-kv :key))
(def ^:private lookup-desc (partial lookup-kv :desc))

(defprotocol ResponseMatchable
  (lookup [x]
    "Lookup a status by code, or description in either string or keyword
    form."))

(extend-protocol ResponseMatchable
  clojure.lang.Keyword
  (lookup [x] (lookup-key x))
  Long
  (lookup [x] (lookup-code x))
  String
  (lookup [x] (lookup-desc x)))

(def status-codes
  "Returns a map of status code to description."
  (into {} (map (juxt :code :desc) store)))

(def codes
  "Returns a set of all HTTP status codes."
  (set (map :code store)))

(def descs
  "Returns a set of descriptions of all HTTP statuses."
  (set (map :desc store)))

(def keys
  "Returns a set of HTTP status descriptions as keywords."
  (set (map :key store)))
