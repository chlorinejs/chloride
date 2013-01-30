# Chloride

Command-line tool to convert js files to Clojure/[Chlorine](https://github.com/chlorinejs/chlorine) ones.
## Get Chloride

You can build chloride with Leiningen:
```
lein uberjar
```

## Usage

You need esparse-cl2 installed before converting files.

```
sudo npm install -g esparse-cl2
```

... and convert!

```
java -jar ${PATH_TO}/chloride-0.1.1-SNAPSHOT-standalone.jar some-dirs-or-files
```

or pipe directly (with `-i` CLI option):

```
echo 'var x = 1' | java -jar ${PATH_TO}/chloride-0.1.1-SNAPSHOT-standalone.jar -i
```

Enjoy it!
## License

Copyright © 2012 Hoang Minh Thang

Distributed under the Eclipse Public License, the same as Clojure.
