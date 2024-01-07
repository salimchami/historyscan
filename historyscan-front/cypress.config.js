"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var cypress_1 = require("cypress");
exports.default = (0, cypress_1.defineConfig)({
    e2e: {
        baseUrl: "http://localhost:4200",
        setupNodeEvents: function (on, config) {
            // implement node event listeners here
        },
    },
});
