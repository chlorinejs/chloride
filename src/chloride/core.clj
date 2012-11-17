(ns chloride.core
  (:use [chlorine.access :only [js->cl2 format-code]]
        [chlorine.esparse :only [check-server]]
        [chlorine.util :only [with-timeout]]
        [clojure.tools.cli :only [cli]])
  (:gen-class :main true))

(defn target-for
  "Generate cl2 file name from js file name."
  [js-file]
  (clojure.string/replace js-file #".js$" ".cl2"))

(defn convert
  "Convert a list of .js files"
  [timeout files]
  (doseq [file files]
    (let [f (.getAbsolutePath file)]
      (println (format "Converting %s..." f))
      (try
        (with-timeout timeout
          (let [content (js->cl2 (slurp f))
                output (apply str (map #(str (format-code %) "\n\n") content))]
            (spit (target-for f)
                  output)))
        (catch java.util.concurrent.TimeoutException e
          (println (format "Time-out converting %s" f))))
      (println "Done!"))))

(defn file? [f]
  (.isFile f))

(defn not-dot-file?
  "A file-filter that removes any file that starts with a dot."
  [f]
  (not= \. (first (.getName f))))

(defn extensions
  "Create a file-filter for the given extensions."
  [& exts]
  (let [exts-set (set (map name exts))]
    (fn [f]
      (let [fname (.getName f)
            idx (.lastIndexOf fname ".")
            cur (if-not (neg? idx) (subs fname (inc idx)))]
        (exts-set cur)))))

(defn get-files [dirs]
  (let [filters [file? not-dot-file? (extensions :js)]
        final-filter #(every? (fn [func] (func %)) filters)]
    (filter final-filter (mapcat #(-> % clojure.java.io/file file-seq) dirs))))

(defn -main [& args]
  (let [[{:keys [rate timeout help]} dirs banner]
        (cli args
             ["-h" "--help" "Show help"]
             ["-t" "--timeout" "Timeout (in millisecond)"
              :parse-fn #(Integer. %)
              :default 5000]
             )]
    (when help
      (println banner)
      (System/exit 0))

    (case (check-server)
      true true
      false  (do (println "ERROR: Something wrong happened. Is the server address correct?")
                 (System/exit 0))
      :error (do (println "ERROR: Server not found! Please start it first!")
                 (System/exit 0)))

    (if (not= [] dirs)
      (do
        (println "")
        (convert timeout (get-files dirs)))
      (println banner))))
